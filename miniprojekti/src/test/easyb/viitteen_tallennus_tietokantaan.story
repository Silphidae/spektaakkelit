import Database.*
import domain.*
import Engine.*

description 'kayttaja pystyy tallentamaan viitteen tietokantaan'

scenario "kayttajan lisaama viite tallentuu tietokantaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot annettu lomakkeella', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
    }

    then 'viite tallentuu tietokantaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "virheellinen viite ei tallennu tietokantaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen virheeliset tiedot annettu lomakkeella', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"vuosi", (Kentta.title):"fdafdsa"])
    }

    then 'viite ei tallennu tietokantaan', {
        db.getSize().shouldBeEqual 0
    }
}

scenario "viitteen haku tietokannasta onnistuu", {
    given 'viitteiden listaus valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"2000", (Kentta.title):"fdafdsa"])
    }

    when 'viitteet haetaan tietokannasta', {
        lista = engine.listaaKaikkiViitteet()
    }

    then 'viite tulostuu ohjelmaan', {
        lista.length.shouldBeEqual 2
    }
}