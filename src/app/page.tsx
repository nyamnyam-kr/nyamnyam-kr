import StoreProvider from './StoreProvider';
import Restaurant from "src/app/(page)/restaurant/page";
import ShowNotice from "src/app/(page)/notice/page";
import {Provider} from "react-redux";
import Home from "src/app/(page)/restaurant/page";

const Page = () => {
    return (
        <StoreProvider>
            <Home />
        </StoreProvider>
    );
};

export default Page;