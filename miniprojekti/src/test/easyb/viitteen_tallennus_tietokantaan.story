import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy tallentamaan viitteen tietokantaan'

scenario "kayttajan lisaama viite tallentuu tietokantaan", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot annettu lomakkeella', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"Kauko Kirjoittaja", (Kentta.journal):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
    }

    then 'viite tallentuu tietokantaan', {
        verify(db, times(1)).insertEntry(any(Viite.class))
    }
}

scenario "virheellinen viite ei tallennu tietokantaan", {
    given 'viitteen lisays valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'viitteen virheeliset tiedot annettu lomakkeella', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"vuosi", (Kentta.title):"fdafdsa"])
    }

    then 'viite ei tallennu tietokantaan', {
        verify(db, never()).insertEntry(any(Viite.class))
    }
}

scenario "viitteen haku tietokannasta onnistuu", {
    given 'viitteiden listaus valittu', {
        db = mock(Database.class)
        engine = new EngineStub(db)
    }

    when 'viitteet haetaan tietokannasta', {
        lista = engine.listaaKaikkiViitteet()
    }

    then 'viitteet haettu tietokannasta', {
        verify(db).getAllEntries()
    }
}