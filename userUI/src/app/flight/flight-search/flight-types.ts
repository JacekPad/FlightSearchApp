export enum FlightTypes {
    ONEWAY = 'ONEWAY',
    ROUND = 'ROUND',
    MULTICITY = 'MULTICITY'
}

export const flightTypesMap = new Map<string, string>()
.set(FlightTypes.ONEWAY, "One-way")
.set(FlightTypes.ROUND, "Round trip")
.set(FlightTypes.MULTICITY, "Multi-city");

