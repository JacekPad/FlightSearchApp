
export interface IPassenger {
    name: string,
    surname: string,
    passengerAge: string
}

export class Passenger {
    public name: string;
    public surname: string;
    public passengerAge: string;

    constructor();
    constructor(obj: IPassenger)
    constructor(obj?: any) {
        this.name = obj && obj.name;
        this.surname = obj && obj.surname;
        this.passengerAge = obj && obj.passengerAge
    }
}