
import Home from './(page)/restaurant/page';
import StoreProvider from './StoreProvider';

const Page = () => {
    return (
        <StoreProvider>
            <Home />
        </StoreProvider>
    );
};

export default Page;