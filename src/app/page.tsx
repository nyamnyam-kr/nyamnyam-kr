import StoreProvider from './StoreProvider';
import Home from "src/app/(page)/restaurant/page";
import './globals.css'

const Page = () => {
    return (
        <StoreProvider>
            <Home />
        </StoreProvider>
    );
};

export default Page;