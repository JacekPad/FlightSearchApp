export enum FlightClassTypes {
    ECONOMY = 'ECONOMY',
    BUISINESS = 'BUSINESS',
    FIRST = 'FIRST'
}

export const flightClassTypesMap = new Map<string, string>()
.set(FlightClassTypes.ECONOMY, 'Economy')
.set(FlightClassTypes.BUISINESS, 'Business')
.set(FlightClassTypes.FIRST, 'First')