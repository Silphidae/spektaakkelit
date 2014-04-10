import Database.*
import domain.*
import Engine.*

description 'kayttaja pystyy luomaan bibtex-tiedoston'

scenario "kayttajan lisaamat viitteet muuttuvat bibtex-muotoon", {
    given 'viitteita on lisatty', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"Joku Nimi", (Kentta.journal):"Lehti",(Kentta.year):"1999", (Kentta.title):"Artikkelinimi"])
    }

    when 'bibtex-tedosto halutaan luoda', {
        bt = new Bibtex(engine)
    }

    then 'viitteet muuttuvat bibtex-muotoisiksi', {
        
    }
}

scenario "kayttaja saa generoitua bibtex-tiedoston", {
    given 'viitteet on lisatty', {
        db = new MockDatabase()
        engine = new EngineStub(db)
        engine.lisaaViite(Viitetyyppi.article, [(Kentta.author):"Joku Nimi", (Kentta.journal):"Lehti",(Kentta.year):"1999", (Kentta.title):"Artikkelinimi"])
    }

    when 'bibtex-tiedosto halutaan luoda', {
        bt = new Bibtex(engine)
    }

    then 'palautuu bibtex-tiedosto', {
        ensure(bt.generoiTiedosto("tiedosto.bib")){
            isNotNull
            and
            is(File)
        }
    }
}


