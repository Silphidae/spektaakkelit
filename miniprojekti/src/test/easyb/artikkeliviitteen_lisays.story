import Database.*
import domain.*
import Engine.*

description 'kayttaja pystyy lisaamaan artikkelityyppisen viitteen'

scenario "kayttajan lisaama viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

    when 'viitteen tiedot kirjattu lomakkeeseen', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"fafadsdfsa", (Kentta.journal):"fasdfdsa",(Kentta.year):"1999", (Kentta.title):"fdafdsa"])
    }

    then 'viite tallennettu ohjelmaan', {
        db.getSize().shouldBeEqual 1
    }
}

scenario "kayttaja syottama viite ei tallennu, jos siina virheita", {
    given 'viitteen lisays valittu', {
        db = new MockDatabase()
        engine = new EngineStub(db)
    }

     when 'syotetaan virheellisia arvoja', {
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):""])
    }

    then 'viite ei tallennu ohjelmaan', {
        engine.listaaKaikkiViitteet().length.shouldBeEqual 0
    }
}


