package org.example.config

import com.typesafe.config.{Config, ConfigFactory}
import org.example.Main.{getClass, logger}
import org.slf4j.LoggerFactory

object AppConfig {

  private val logger = LoggerFactory.getLogger(getClass)
  def load(environment: String): Config = {
    val configFileName = s"application-${environment}.conf"
    val config = ConfigFactory.load(configFileName)
    logger.info(s"Successfully loaded configuration from: $configFileName")
    return config
  }
}