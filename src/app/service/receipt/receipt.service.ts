import {receipt} from "src/app/api/receipt/receipt.api";
import {CountCost} from "src/app/model/dash.model";


export const fetchReceiptRegister = async (formData: FormData): Promise<any> => {
    const data = await receipt.receiptRegister(formData);
    return data;
};

export const fetchReceiptCost = async (id : number) : Promise<CountCost[]>  => {
    const data = await receipt.receiptCost(id);
    return data;
}
