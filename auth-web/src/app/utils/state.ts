export class State {

    private items:any[] = [
        {"isoCode":"AC", "name": "Acre"},
        {"isoCode":"AL", "name": "Alagoas"},
        {"isoCode":"AP", "name": "Amapá"},
        {"isoCode":"AM", "name": "Amazonas"},
        {"isoCode":"BA", "name": "Bahia"},
        {"isoCode":"CE", "name": "Ceará"},
        {"isoCode":"DF", "name": "Distrito Federal"},
        {"isoCode":"ES", "name": "Espírito Santo"},
        {"isoCode":"GO", "name": "Goiás"},
        {"isoCode":"MA", "name": "Maranhão"},
        {"isoCode":"MS", "name": "Mato Grosso do Sul"},
        {"isoCode":"MT", "name": "Mato Grosso"},
        {"isoCode":"MG", "name": "Minas Gerais"},
        {"isoCode":"PA", "name": "Pará"},
        {"isoCode":"PB", "name": "Paraíba"},
        {"isoCode":"PR", "name": "Paraná"},
        {"isoCode":"PE", "name": "Pernambuco"},
        {"isoCode":"PI", "name": "Piauí"},
        {"isoCode":"RJ", "name": "Rio de Janeiro"},
        {"isoCode":"RN", "name": "Rio Grande do Norte"},
        {"isoCode":"RS", "name": "Rio Grande do Sul"},
        {"isoCode":"RO", "name": "Rondônia"},
        {"isoCode":"RR", "name": "Roraima"},
        {"isoCode":"SP", "name": "São Paulo"},
        {"isoCode":"SC", "name": "Santa Catarina"},
        {"isoCode":"SE", "name": "Sergipe"},
        {"isoCode":"TO", "name": "Tocantins"}
    ]

    public get states(): any[] {
        return this.items;
      }
}