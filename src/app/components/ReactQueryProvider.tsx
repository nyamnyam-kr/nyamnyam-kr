"use client"
import React from 'react';
import {QueryClient, QueryClientProvider} from '@tanstack/react-query';
import {ReactQueryDevtools} from '@tanstack/react-query-devtools';
import { ReactQueryStreamedHydration } from '@tanstack/react-query-next-experimental';
import { makeStore } from 'src/lib/store';
import { Provider } from 'react-redux';


function Providers({ children }: React.PropsWithChildren) {
    const [client] = React.useState(new QueryClient());
    const store = makeStore();

    return (
        <QueryClientProvider client={client}>
            <Provider store={store}>
            <ReactQueryStreamedHydration>{children}</ReactQueryStreamedHydration>
            <ReactQueryDevtools initialIsOpen={false} />
            </Provider>
        </QueryClientProvider>
    );
}

export default Providers;
