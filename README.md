# wth-pons-anki-app

#### This project is under heavy development.

This is a full application that integrates:
- [pons.eu](https://pons.eu/) as a translation service
- [anki](https://apps.ankiweb.net/) as a flashcard/repository service

Configurable via a [zio-config](https://zio.github.io/zio-config/).

Configuration file template:
```json
{
  words_path: "path to file with phrases to be queried",
  anki: {
    deck_name: "name::of::anki::deck::to:be::populated"
  },
  pons: {
    source: "en",
    target: "es",
    secret: "access token from pons.eu"
  }
}
```
Run:
```
-Dconfig.file=/path/to/configuration/file.above
```

**Note:** Run [Anki](https://apps.ankiweb.net/) with [anki-connect](https://ankiweb.net/shared/info/2055492159) add-on before running the wth application.

More to come.