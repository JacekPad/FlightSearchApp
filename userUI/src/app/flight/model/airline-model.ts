export interface IAirline {
    id: string,
    iata: string,
    name: string,
    logo_url: string
}

export class Airline {
    public id: string;
    public iata: string;
    public name: string;
    public logo_url: string

    constructor()
    constructor(obj: IArguments)
    constructor(obj?: any) {
        this.id = obj && obj.id;
        this.iata = obj && obj.iata;
        this.name = obj && obj.name;
        this.logo_url = obj && obj.logo_url;
    }
}