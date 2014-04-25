import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy poistamaan viitteesta tagin'

scenario "kayttaja voi poistaa tagin valitulta viitteelta", {
    given 'viite valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }
    when 'poistetaan tagi viitteelta', {
        engine.removeTagi("ckey", "tagi")
    }

    then 'tagiviite poistetaan tietokannasta', {
        verify(db).removeTagFromViite("ckey", "tagi")
    }
}