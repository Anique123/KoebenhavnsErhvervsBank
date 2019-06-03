# KoebenhavnsErhvervsBank

Jeg har valgt at lave en bankapp, kaldet KøbenhavnsErhvervsbank til eksamensprojektet i Android App. Et af kravene til opgaven skulle have 2 activities, her har min app fokus på login activity og transfer/paybill activity. Man kan overfører penge fra sine 2 budgetkontier som er defaultaccount og budgetaccount. 
Man logger ind med sit cpr og personlige kode. Hvis man ikke har en bruger kan man oprette en bruger hvor man automatisk får tilføjet 2 accounts med 1000 i en af kontoerne. Applikationen læser fra og skriver til en realtime database hos firebase der er integreret med appen.
Main menu siden har 4 bokse hvor det kun er budget-boksen der virker.

Hvis der problemer med at køre appen skal der laves en gradle clean via terminalen(./gradlew clean) - dette kan ske når man henter en android app fra github. 

