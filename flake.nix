{
  description = "A Nix-flake-based Kotlin + Gradle + Android dev environment with OpenJDK (flake-utils)";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-unstable";
    android-nixpkgs.url = "github:tadfisher/android-nixpkgs";
    gradle2nix.url = "github:tadfisher/gradle2nix/v2";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, android-nixpkgs, gradle2nix, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
    let
      pkgs = import nixpkgs {
        inherit system;
        config.allowUnfree = true;
      };

      # Android SDK for this system
      androidSdk = android-nixpkgs.sdk.${system} (sdkPkgs: with sdkPkgs; [
        cmdline-tools-latest
        build-tools-36-0-0
        platform-tools
        platforms-android-36
        emulator
      ]);

      jdk = pkgs.javaPackages.compiler.openjdk21;

      idea-launcher = pkgs.writeShellScriptBin "idea" ''
        exec ${pkgs.jetbrains.idea-ultimate}/bin/idea-ultimate \
          -Dawt.toolkit.name=WLToolkit "$@" \
          >/dev/null 2>&1 &
      '';
    in
    {
      packages = {
        inherit jdk idea-launcher;

        melay-app-jvm = gradle2nix.builders.${system}.buildGradlePackage {
          pname = "melay-app-jvm";
          version = "1.0.0";
          lockFile = ./gradle.lock;
          src = ./.;
          gradleInstallFlags = [ ":app:packageReleaseUberJarForCurrentOS" ];

          postInstall = ''
            mkdir -p $out/melay/app

            case "${system}" in
              x86_64-linux)
                archJar="melay-linux-x64-1.0.0-release.jar"
                ;;
              aarch64-linux)
                archJar="melay-linux-aarch64-1.0.0-release.jar"
                ;;
              *)
                echo "Unsupported system: ${system}" >&2
                exit 1
                ;;
            esac

            cp app/build/compose/jars/$archJar $out/melay/app/$archJar
          '';
        };
      };

      apps = {
        gradle2nix = gradle2nix.apps.${system}.default;
      };

      devShells.default = pkgs.mkShell {
        packages = with pkgs; [
          gcc
          gradle_9
          kotlin
          jdk
          ncurses
          patchelf
          zlib
          libGL
          xorg.libX11
          jetbrains.idea-ultimate
          idea-launcher
          androidSdk
        ];

        shellHook = ''
          export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:${
            pkgs.lib.makeLibraryPath [
              pkgs.libGL
              pkgs.xorg.libX11
            ]
          }

          # Java
          export JAVA_HOME=${jdk}/lib/openjdk
          export PATH=$JAVA_HOME/bin:$PATH

          # Gradle
          export GRADLE_HOME=${pkgs.gradle_9}/lib/gradle
          export PATH=$GRADLE_HOME/bin:$PATH

          # Android
          export ANDROID_HOME=${androidSdk}/share/android-sdk
          export PATH=$ANDROID_HOME/bin:$PATH

          # Kotlin
          export PATH=${pkgs.kotlin}/bin:$PATH

          # Optional: force IntelliJ to use Nix-provided Gradle
          export ORG_GRADLE_PROJECT_gradleUserHome=$GRADLE_HOME
        '';
      };
    });
}
