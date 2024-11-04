# Class Name Analyser

## About project

Application that determines popularity of words in class names in Java/Kotlin based popular projects published on GitHub.

Due to GitHub API request rate limits, fetches data sequentially over WebSocket connection.

## Quick overiew

### Main page

![kotlin main page](/readme-assets/kotlin-main.png)

![java main page](/readme-assets/java-main.png)

### Analysis screen

![analysis](/readme-assets/analysis.png)

Video presentation is available [here](https://youtu.be/d_yw3Qxh5P4)

## How to run

### Generate GitHub Personal Access Token

Generate fine-grained personal access token [here](https://github.com/settings/personal-access-tokens/new) or read more about it [here](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens).

### Set environment variables

In root directory there is [.env file](.env), set your GitHub Access Token generated in precious step.

```Dotenv
GITHUB_TOKEN=<Your Github Access Token>
```

You can optionally set ports the application will be available on.

### Run on Docker

With Docker installed run following from project root directory:

```bash
docker compose up -d
```

### Exit

To stop application run:

```bash
docker compose stop
```

You can start it again with:

```bash
docker compose start
```

Or remove application containers:

```bash
docker compose down
```

## Assumptions

- Assumes 1 class per file with name matching filename
- Checks only classes with UpperCamelCase naming convention - follows best practices:
  - starting with lowercase letter is invalid
  - using all uppercase words e.g.`HTTPClient` is invalid
  - using non-alphabetical characters is invalid
- What follows is that analysed entities include:
  - `classes`,
  - `abstract classes`,
  - `interfaces`,
  - `enums`,
  - `java records`,
  - `@annotations`,
  - `kotlin data classes`
  - `kotlin singleton objects`

---

Thanks for checking my project out!
