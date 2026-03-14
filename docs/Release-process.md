# JFileTreePrettyPrinter release process

- **In `develop` branch:**
  - Run tests
  - Ensure `develop` branch is [Sonar-ready](https://sonarcloud.io/project/overview?id=jinputguard_jinputguard&branch=develop)

- **Merge `develop` into `main` branch**
  - Note: `pom.xml` must have `X.Y.Z-SNAPSHOT` version

- **In `main` branch:**
  - Set project version in `pom.xml` to `X.Y.Z` (remove the `-SNAPSHOT`)
  - Update `README.md` (with new `X.Y.Z` dependency version)
  - Update `CHANGELOG.md` with changes **!IMPORTANT: Respect format!**
  - Update `ROADMAP.md` if necessary
  - Update `SECURITY.md` if necessary
  - Commit locally
  - Tag with appropriate `vX.Y.Z`
  - Push code & tag

- **In Github:**
  - Action `create-github-release` triggers automatically on tag push `vX.Y.Z` on `main` branch
    - Builds native executables for Linux/Windows/MacOS
    - Creates a **draft** Github release with:
      - Executables as attached zipped files
      - Appropriate section of `CHANGELOG.md` for this version
  - Review the Github release and **publish it manually**
  - Github `publish-maven-release` workflow will run automatically
    - Few minutes later, artifact is available on Maven Central 🎉
  - Update wiki if required

- **Merge `main` back into `develop` branch**
  - Note: `pom.xml` now has `X.Y.(Z+1)-SNAPSHOT` version