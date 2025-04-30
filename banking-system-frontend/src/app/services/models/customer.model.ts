import { Account } from "./account.model";

export interface Customer {
    id: number;
    fullName: string;
    email: string;
    dateOfBirth: Date;
    accounts: Account[];
}