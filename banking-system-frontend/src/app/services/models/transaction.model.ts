export interface Transaction {
    id: number;
    accountId: number;
    type: 'Deposit' | 'Withdrawal';
    amount: number;
    date: Date;
}