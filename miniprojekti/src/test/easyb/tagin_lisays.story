import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy lisaamaan viitteeseen tagin'

scenario "kayttaja voi lisata tagin valitulle viittelle", {
    given 'viite valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }
    when 'lisataan tagi viitteelle', {
        engine.addTagi("ckey", "tagi")
    }

    then 'tagiviite lisataan tietokantaan', {
        verify(db).addTag("ckey", "tagi")
    }
}