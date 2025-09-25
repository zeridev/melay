{
  description = "A Nix-flake-based Kotlin + Gradle dev environment with GraalVM";

  inputs.nixpkgs.url = "https://flakehub.com/f/NixOS/nixpkgs/0.1";

  outputs =
    inputs:
    let
      supportedSystems = [
        "x86_64-linux"
        "aarch64-linux"
        "x86_64-darwin"
        "aarch64-darwin"
      ];

      forEachSupportedSystem =
        f:
        inputs.nixpkgs.lib.genAttrs supportedSystems (
          system:
          f {
            pkgs = import inputs.nixpkgs {
              inherit system;
			  config.allowUnfree = true;
              overlays = [ inputs.self.overlays.default ];
            };
          }
        );
    in
    {
      overlays.default =
        final: prev:
        let
          jdk = prev.graalvmPackages.graalvm-oracle;
        in
        {
          gradle =
            if prev ? gradle_9 then
              prev.gradle_9.override { java = jdk; }
            else
              prev.gradle.override { java = jdk; };

          kotlin = prev.kotlin.override { jre = jdk; };

          # Expose the JDK itself explicitly so you can add it to devShell
          graalvm = jdk;
        };

      devShells = forEachSupportedSystem (
        { pkgs }:
        {
          default = pkgs.mkShell {
            packages = with pkgs; [
              gcc
              gradle
              kotlin
              graalvm
              ncurses
              patchelf
              zlib
            ];

			JAVA_TOOL_OPTIONS = "--enable-native-access=ALL-UNNAMED";
			SPRING_PROFILES_ACTIVE= "dev";

			shellHook = ''
				# Define colorsRED
				RED="\033[31m"
				GREEN="\033[32m"
				BLUE="\033[34m"
				YELLOW="\033[33m"
				RESET="\033[0m"

				# Use shell variables with single $ inside the string
				echo -e "$RED Java:    $(java -version 2>&1 | sed -n '2p') $RESET"
				echo -e "$GREEN Kotlin:  $(kotlinc -version 2>&1 | sed -n '2p') $RESET"
				echo -e "$BLUE Gradle:  $(gradle -version 2>&1 | head -n4 | tail -n1) $RESET"
				echo -e "$YELLOW Welcome to the GraalVM + Kotlin devshell! $RESET"
			'';
          };
        }
      );
    };
}

