import axios from "axios";
import { AxiosResponse } from "axios";

export async function getTipiMerce(): Promise<AxiosResponse<any, any, {}>>{
    const response = await axios.get("http://localhost:8090/api/v1/merce/tipi-disponibili");

    return response;
}