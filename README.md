[![Java CI with Maven](https://github.com/kristiania-pgr203-2021/pgr203-innlevering-3-sanderwe/actions/workflows/maven.yml/badge.svg)](https://github.com/kristiania-pgr203-2021/pgr203-innlevering-3-sanderwe/actions/workflows/maven.yml)
# Innlevering 3: JDBC

## Oppgave

Lag 2 data access objects med tilhørende tester og sett opp Github Actions for å kjøre testene med Maven.

## Innlevering

I innlevering 2 opprettet dere en webapplikasjon som lar brukeren legge til og liste opp produkter. I innlevering 3 skal dere lage databasekoden for produkter og produktkategorier. Dere skal levere klasser for objektene (produkt og kategori), data access objects og tester for DAO-ene.

* Det skal være mulig å opprette produkter og produktkategorier i databasen. Dere skal ha tester som demonstrerer dette
* Produkt skal ha navn, beskrivelse, pris og id for produktkategori. Alle skal lagres i basen. Dere skal ha tester som demonstrerer dette
* Det skal være mulig å liste ut alle kategorier og liste ut alle produkter som tilhører en produktkategori. Dere skal ha tester som demonstrerer dette

Innleveringen skal leveres på Canvas med link til GitHub-repository. Begge skal levere i Canvas (med link til samme repository).

Det anbefales at dere benytter (remote) pair programming for innlevering 3 og jobber på en "ping-pong" branch som dere oppretter og merger en pull request for tilsvarende som dere gjorde for innlevering 1, men det er ikke et krav.


## Krav til innlevering

* Innlevering
  * [x] Begge har levert link til Github Repository i Canvas
  * [x] Koden er sjekket inn på et repository på https://github.com/kristiania-pgr203-2021
  * [x] GitHub repository er private. Dere skal gi tilgang til de som gir code review
* Github
  * [ ] Valgfritt: Dere har en Git branch som viser parprogrammering der dere sjekker inn annenhver gang
  * [ ] Valgfritt: Dere har mottatt code review fra medstudenter
  * [x] `.idea`, `*.iml` og `target` er lagt til i .gitignore og ikke sjekket inn
  * [x] Github Actions rapporterer at 100% av testene kjører grønt <-- kjørte grønt fram til Github actions gikk tom for minutter
  * [x] GitHub Actions skal ha `timeout` på bygget
  * [x] Github Actions bruker Java 11
  * [x] README viser GitHub Actions status badge
* Kode
  * [x] Koden skal være korrekt strukturert etter Maven sine standarder (koden skal ligge under `src/main/java` og tester under `src/test/java`)
  * [x] Klassene skal ligge i Java packages
  * [x] Klassenavn, pakkenavn, metodenavn og variabelnavn skal følge Java-konvensjoner når det gjelder små og store bokstaver
  * [x] Koden skal være korrekt indentert
  * [x] Tester demonstrerer opprettelse av både produkt og produktkategori
  * [x] Alle feltene for produkt skal være lagret i databasen
  * [x] Tester demonsterer listing av produkter og kategorier
