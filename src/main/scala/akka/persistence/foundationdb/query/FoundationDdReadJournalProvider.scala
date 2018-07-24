package akka.persistence.foundationdb.query

import akka.actor.ExtendedActorSystem
import akka.persistence.foundationdb.query.scaladsl.FoundationDbReadJournal
import akka.persistence.query.javadsl
import akka.persistence.query.ReadJournalProvider
import com.typesafe.config.Config

import scala.util.control.NonFatal

class FoundationDdReadJournalProvider(system: ExtendedActorSystem, config: Config) extends ReadJournalProvider {


    override val scaladslReadJournal: FoundationDbReadJournal =
      try {
        new FoundationDbReadJournal(system, config)
      } catch {
        case NonFatal(e) =>
          // TODO can be removed when https://github.com/akka/akka/issues/18976 is fixed
          system.log.error(e, "Failed to initialize FoundationDbReadJournal")
          throw e
      }

//    override val javadslReadJournal = () => new ReadJournal {}
  override def javadslReadJournal(): javadsl.ReadJournal = new javadsl.ReadJournal {}
}

