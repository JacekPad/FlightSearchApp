
export interface IFlightEntity {
    id: string
}

export class FlightEntity {
    public id: string;

    constructor();
    constructor(obj: IFlightEntity)
    constructor(obj?: any) {
        this.id = obj && obj.id;

    }
}