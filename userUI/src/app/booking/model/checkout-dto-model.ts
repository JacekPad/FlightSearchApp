import { IBookingEntity } from "./booking-entity-model";
import { ICheckoutInfo } from "./checkout-info-model";

export interface ICheckoutDTO {
    checkoutInfo: ICheckoutInfo,
    bookingEntity: IBookingEntity
}

export class CheckoutDTO {
    public checkoutInfo: ICheckoutInfo;
    public bookingEntity: IBookingEntity;

    constructor();
    constructor(obj: ICheckoutDTO)
    constructor(obj?: any) {
        this.checkoutInfo = obj && obj.checkoutInfo;
        this.bookingEntity = obj && obj.bookingEntity;
    }
}