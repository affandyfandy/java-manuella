import { Routes } from '@angular/router';
import { RouterConfig } from './config/app.constants';

export const routes: Routes = [
    {
        path: RouterConfig.HOME.path,
        loadChildren: () =>
            import('./pages/home/home.routes')
                .then(m => m.homeRoutes)
    },
    {
        path: RouterConfig.PRODUCT.path,
        loadChildren: () =>
            import('./pages/product/product.routes')
                .then(m => m.productRoutes)
    },
    { path: '**', redirectTo: '/' }
];