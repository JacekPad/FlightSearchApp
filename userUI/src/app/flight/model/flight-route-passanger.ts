export interface IFLightRoutePassanger {
    type: string
    number: number
}

export class FlightRoutePassanger {
    public type: string;
    public number: number

    constructor()
    constructor(obj: IFLightRoutePassanger)
    constructor(obj?: any) {
        this.type = obj && obj.type;
        this.number = obj && obj.number
    }
}