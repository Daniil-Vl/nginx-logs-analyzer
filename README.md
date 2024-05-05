# Nginx logs analyzer

**Prerequisites: JDK-21**

## Getting started

1. Build the project

```bash
./gradlew uberJar
```

2. Run project

```bash
java -jar nginx-logs-analyzer-1.0-SNAPSHOT-uber.jar ...options
```

## CLI options

**1. Paths**

Paths of files with nginx logs to analyze

```bash
java -jar ... --paths path1 path2 ...
```

**2. From date**

The date and time from which logs will be analyzed

```bash
java -jar ... --from yyyy-MM-ddTHH:mm:ss.ns+hh:mm (offset) ...
```

**3. To date**

The date and time to which logs will be analyzed

```bash
java -jar ... --to yyyy-MM-ddTHH:mm:ss.ns+hh:mm (offset) ...
```

**4. Output format**

Format for output report

Candidates: MARKDOWN, ADOC

```bash
java -jar ... --format MARKDOWN ... 
```