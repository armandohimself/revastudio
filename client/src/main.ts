import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';
import { provideZoneChangeDetection } from '@angular/core';

import { AppComponent } from './app/app.component';
import { appRoutes } from './app/app.routes'
import { environment } from './environments/environment';

if (environment.production) {
  // Production mode is handled automatically in newer Angular versions
}

bootstrapApplication(AppComponent, {
  providers: [provideZoneChangeDetection(), provideRouter(appRoutes)],
}).catch((err) => console.error(err));
