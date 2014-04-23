import Database.*
import domain.*
import Engine.*
import static org.mockito.Mockito.*;

description 'kayttaja pystyy muokkaamaan viitteitään'

scenario "kayttaja voi muokata valittua viitettä", {
    given 'viite valittu ja viitteen muokkaus valittu'
    when 'muokkaukset kirjattu lomakkeelle'
    then 'muokkaukset tallentuvat tietokantaan'
}