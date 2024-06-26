export enum PassangerType {
    ADULT = 'ADULT',
    CHILD = 'CHILD',
    INFANT = 'INFANT'
}

export const passangerTypeMap = new Map<string, string>()
.set(PassangerType.ADULT, 'Adult')
.set(PassangerType.CHILD, 'Child')
.set(PassangerType.INFANT, 'Infant')