package wth.model

import wth.model.anki.AnkiConfig
import wth.model.pons.PonsQueryConf

case class Config(words_path: String, anki: AnkiConfig, pons: PonsQueryConf)
