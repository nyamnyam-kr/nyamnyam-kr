import StoreProvider from './StoreProvider';
import Home from "src/app/(page)/restaurant/page";
import './globals.css'
import {GlobalStyle} from "src/app/types/global.style";
import {Provider} from "react-redux";
import {store} from "src/lib/store";


const Page = () => {
    return (
        <StoreProvider>
            <Home/>
        </StoreProvider>
    );
};

export default Page;