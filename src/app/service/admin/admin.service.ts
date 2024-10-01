import {showCount} from "src/app/api/admin/admin.api";
import {CountItem} from "src/app/model/dash.model";



export const fetchShowCount = async (): Promise<CountItem[]> => {
    const data = await showCount();
    return data.slice(0, 5);
};