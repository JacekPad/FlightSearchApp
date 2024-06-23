
export interface ICheckoutInfo {
    currency: string,
    price: number,
    successUrl: string
    cancelUrl: string,
    routeId: string
}

export class CheckoutInfo {
    public currency: string;
    public price: number;
    public successUrl: string;
    public cancelUrl: string;
    public routeId: string;

    constructor();
    constructor(obj: ICheckoutInfo)
    constructor(obj?: any) {
        this.currency = obj && obj.currency;
        this.price = obj && obj.price;
        this.successUrl = obj && obj.successUrl;
        this.cancelUrl = obj && obj.cancelUrl;
        this.routeId = obj && obj.routeId;

    }
}