# Masakari

Tool to detect unused entries in your dictionary files.

## Usage

Download `masakari` executable from releases. Make it executable (`chmod +x masakari`). Execute.

```
./masakari /path/to/folder/to/check
```

It checks the given path recursively. If you omit the path argument it will use the current folder (`.`).

## Limitations

Currently only works with Vue.js code and JSON dictionary files. It assumes that every JSON file is a dictionary.

## Building for yourself

Downloading the binary from Github is too slow? You want to build for yourself?

The simple way is using my `clojure-graal` Docker image:

```
docker run --rm -v $(pwd):/build -w /build valerauko/clojure-graal
```

Or, if you have all the necessary parts (Leiningen, Graal native-image) ready locally, then running `lein native-image` should yield the same result as well.
