export interface IPassangerCount {
    code: string,
    value: string,
    maxQuantity: number[]
}

export class PassangerCount {
    public code: string;
    public value: string;
    public maxQuantity: number[]

    constructor();
    constructor(obj: IPassangerCount)
    constructor(obj?: any) {
        this.code = obj && obj.code
        this.value = obj && obj.value
        this.maxQuantity = obj && obj.maxQuantity
    }
}