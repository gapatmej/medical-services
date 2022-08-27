import { Pipe, PipeTransform } from '@angular/core';

import * as dayjs from 'dayjs';

@Pipe({
  name: 'formatMediumDate',
})
export class FormatMediumDatePipe implements PipeTransform {
  transform(day: dayjs.Dayjs | null | undefined): string {
    console.error('transform', day);
    return day ? day.format('D MMM YYYY') : '';
  }
}
