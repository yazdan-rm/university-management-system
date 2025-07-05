import { CacheProvider } from '@emotion/react';
import createCache from '@emotion/cache';
import rtlPlugin from 'stylis-plugin-rtl';

// Create RTL cache
const cacheRtl = createCache({
    key: 'muirtl',
    stylisPlugins: [rtlPlugin],
});

export function RTL({ children }: { children: React.ReactNode }) {
    return <CacheProvider value={cacheRtl}>{children}</CacheProvider>;
}