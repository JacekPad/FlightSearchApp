export interface IFLightRoutePassanger {
    type: string
    quantity: number
}

export class FlightRoutePassanger {
    public type: string;
    public quantity: number

    constructor()
    constructor(obj: IFLightRoutePassanger)
    constructor(obj?: any) {
        this.type = obj && obj.type;
        this.quantity = obj && obj.quantity
    }
}