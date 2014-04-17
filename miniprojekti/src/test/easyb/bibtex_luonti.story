import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy luomaan bibtex-tiedoston'

scenario "kayttaja saa generoitua bibtex-tiedoston", {
    given 'viitteet on lisatty', {
        db = mock(Database.class)
        engine = new EngineStub(db)
        when(engine.getViitteet()).thenReturn(new ArrayList<Viite>());
    }

    when 'bibtex-tiedosto halutaan luoda', {
        bt = new Bibtex(engine)
    }

    then 'palautuu bibtex-tiedosto', {
        ensure(bt.generoiTiedosto("tiedosto.bib")){
            isAFile
        }
        bt.generoiTiedosto("tiedosto.bib").shouldNotBe null
    }
}

scenario "tiedosto ei muodostu ilman nimea", {
    given 'viitteet on lisatty', {
        db = mock(Database.class)
        engine = new EngineStub(db)
        when(engine.getViitteet()).thenReturn(new ArrayList<Viite>());
    }

    when 'bibtex-tiedosto halutaan luoda', {
        bt = new Bibtex(engine)
    }

    then 'palautuu virheilmoitus', {
        bt.generoiTiedosto("").shouldBe null
    }
}