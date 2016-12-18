import { NgModule }      from '@angular/core';
import { CommonModule }  from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgaModule } from '../../theme/nga.module';
import { BaThemeConfigProvider } from '../../theme';

import { Dashboard } from './dashboard.component';
import { routing }       from './dashboard.routing';

import { TodoComponent } from './todo';
import { Calendar } from './calendar';
import { CalendarService } from './calendar/calendar.service';
import { TodoService } from './todo';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    NgaModule,
    routing
  ],
  declarations: [
    TodoComponent,
    Calendar,
    Dashboard
  ],
  providers: [
    CalendarService,
    TodoService,
    BaThemeConfigProvider
  ]
})
export default class DashboardModule {}
