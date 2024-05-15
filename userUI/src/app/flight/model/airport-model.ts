export interface IAiport {

    id: string,
    iata: string,
    latitude: string,
    longitude: string,
    name: string,

}

export class Airport {
    public id: string;
    public iata: string;
    public latitude: string;
    public longitude: string;
    public name : string

    constructor()
    constructor(obj: IAiport)
    constructor(obj?: any) {
        this.id = obj && obj.id;
        this.iata = obj && obj.iata;
        this.latitude = obj && obj.latitude;
        this.longitude = obj && obj.longitude
        this.name = obj && obj.name;
    }
}