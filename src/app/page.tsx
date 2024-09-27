
import Home from 'src/app/(page)/restaurant/page';
import './globals.css'
import StoreProvider from './StoreProvider';


const Page = () => {
    return (
        <StoreProvider>
            <Home />
        </StoreProvider>
    );
};

export default Page;