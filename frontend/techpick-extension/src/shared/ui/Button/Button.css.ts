import { style, styleVariants } from '@vanilla-extract/css';

export const buttonSizeVariants = styleVariants({
  xs: {
    height: '24px',
    padding: '0 8px',
    fontSize: '0.75rem', // 12px
  },
  sm: {
    height: '32px',
    padding: '0 12px',
    fontSize: '0.875rem', // 14px
  },
  md: {
    height: '40px',
    padding: '0 16px',
    fontSize: '1rem', // 16px
  },
  lg: {
    height: '48px',
    padding: '0 24px',
    fontSize: '1.125rem', // 18px
  },
  xl: {
    height: '56px',
    padding: '0 32px',
    fontSize: '1.25rem', // 20px
  },
});

export type buttonSizeVariantKeyTypes = keyof typeof buttonSizeVariants;

export const buttonColorVariants = styleVariants({
  black: {
    color: 'black',
  },
  white: {
    color: 'white',
  },
});
export type buttonColorVariantKeyTypes = keyof typeof buttonColorVariants;

export const buttonBackgroundVariants = styleVariants({
  primary: {
    backgroundColor: '#444C52',
  },
  secondary: {
    backgroundColor: '#666666',
  },
});

export type buttonBackgroundVariantKeyTypes =
  keyof typeof buttonBackgroundVariants;

export const buttonStyle = style({
  cursor: 'pointer',
  borderRadius: '4px',

  ':hover': {
    backgroundColor: 'black', // hover 시 배경색
    transition: 'background-color 0.3s ease', // 애니메이션 효과
  },
});
