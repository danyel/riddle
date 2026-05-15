export class Strings {
    static EMPTY: string = '';

    static isEmpty(value?: string): boolean {
        return !this.isNotEmpty()
    }

    static isNotEmpty(value?: string): boolean {
        return !this.isBlank(value);
    }

    static isBlank(value?: string): boolean {
        return !this.isNull(value) && value?.trim() === '';
    }

    static isNull(value?: string): boolean {
        return this.isUndefined(value) || value === null;
    }

    static isUndefined(value?: string): boolean {
        return value === undefined
    }
}