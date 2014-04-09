import Database.*
import domain.*
import Engine.*

description 'kayttaja pystyy tallentamaan viitteen tietokantaan'

scenario "kayttajan lisaama viite tallentuu tietokantaan", {
    given 'viitteen lisays valittu', {
        db = new Database()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot annettu lomakkeella', {
        engine.lisaaViite("key", "author", "title", "published", 1999)
    }

    then 'viite tallentuu tietokantaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "virheellinen viite ei tallennu tietokantaan", {
    given 'viitteen lisays valittu', {
        db = new Database()
        engine = new EngineStub(db)
    }

    when 'viitteen virheeliset tiedot annettu lomakkeella', {
        engine.lisaaViite("key", "author", "", "published", 1999)
    }

    then 'viite ei tallennu tietokantaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "viitteen haku tietokannasta onnistuu", {
    given 'viitteiden listaus valittu', {
        db = new Database()
        engine = new EngineStub(db)
        engine.lisaaArticle("key", "kirjoittaja", "paperin nimi", "lehti", 12, 212, 1972, 68, 99)
        engine.lisaaArticle("key2", "kirjoitaja2", "paras artikkeli", "lehti", 10, 188, 1670, 12, 18)
    }

    when 'viitteet haetaan tietokannasta', {
        lista = engine.listaaKaikkiViitteet()
    }

    then 'viite tulostuu ohjelmaan', {
        lista.length.shouldBeEqual 2
    }
}