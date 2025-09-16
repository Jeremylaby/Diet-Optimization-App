import 'styled-components';
import { lightTheme } from './theme';

type Theme = typeof lightTheme;
/* eslint-disable @typescript-eslint/no-empty-object-type */
declare module 'styled-components' {
  export interface DefaultTheme extends Theme {}
}
