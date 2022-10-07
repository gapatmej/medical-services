import dayjs from 'dayjs';

export const calculateDays = (from: dayjs.Dayjs | null | undefined, to: dayjs.Dayjs | null | undefined): number => {
  if (from && to) {
    return to.diff(from, 'day');
  }
  return 0;
};
