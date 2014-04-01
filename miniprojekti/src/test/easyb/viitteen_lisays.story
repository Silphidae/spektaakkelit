description 'kayttaja pystyy lisaamaan artikkelityyppisen viitteen'

scenario "kayttajan lisaama viite tallentuu ohjelmaan", {
    given 'viitteen lisays valittu'
    when 'viitteen tiedot kirjattu lomakkeeseen'
    then 'viite tallennettu ohjelmaan'
}