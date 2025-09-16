import { useTheme } from 'styled-components';

import { useThemeMode } from '../../theme/AppThemeProvider.tsx';
import { AppButton } from '../../components';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCircleUser } from '@fortawesome/free-solid-svg-icons';

const Themes = () => {
  const { toggle } = useThemeMode();
  const theme = useTheme();

  const { borderRadius, borderWidth, breakpoints, typography } = theme;
  const { colors } = theme;
  return (
    <div>
      <button
        onClick={toggle}
        style={{
          padding: '0.5rem 1rem',
          borderRadius: borderRadius.md,
          borderWidth: borderWidth.normal,
          borderStyle: 'solid',
          borderColor: colors.primary[500],
          backgroundColor: colors.primary[100],
          color: colors.primary[900],
          cursor: 'pointer',
          marginBottom: '2rem',
        }}
      >
        Przełącz motyw (Light / Dark)
      </button>

      <section>
        <h2>Border Radius</h2>
        <ul>
          {Object.entries(borderRadius).map(([key, value]) => (
            <li key={key}>
              <strong>{key}:</strong>{' '}
              <span
                style={{
                  display: 'inline-block',
                  width: '30px',
                  height: '30px',
                  backgroundColor: colors.primary[500],
                  borderRadius: value,
                }}
              />{' '}
              ({value})
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Border Width</h2>
        <ul>
          {Object.entries(borderWidth).map(([key, value]) => (
            <li key={key}>
              <strong>{key}:</strong>{' '}
              <div
                style={{
                  width: '100px',
                  borderBottom: `${value} solid ${colors.primary[500]}`,
                }}
              />{' '}
              ({value})
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Breakpoints</h2>
        <ul>
          {Object.entries(breakpoints).map(([key, value]) => (
            <li key={key}>
              <strong>{key}:</strong> {value}
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Typography</h2>
        <p>
          <strong>Font Family:</strong> {typography.fontFamily}
        </p>

        <h3>Font Sizes</h3>
        <ul>
          {Object.entries(typography.fontSize).map(([key, value]) => (
            <li key={key} style={{ fontSize: value }}>
              {key} ({value}) — przykładowy tekst
            </li>
          ))}
        </ul>

        <h3>Font Weights</h3>
        <ul>
          {Object.entries(typography.fontWeight).map(([key, value]) => (
            <li key={key} style={{ fontWeight: value }}>
              {key} ({value}) — przykładowy tekst
            </li>
          ))}
        </ul>
      </section>

      <section>
        <h2>Colors</h2>
        {Object.entries(colors).map(([colorGroup, groupValue]) => {
          if (typeof groupValue !== 'object') {
            return (
              <div key={colorGroup}>
                <strong>{colorGroup}:</strong> {groupValue}
              </div>
            );
          }
          return (
            <div key={colorGroup} style={{ marginBottom: '1rem' }}>
              <strong>{colorGroup}:</strong>
              <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
                {Object.entries(groupValue).map(([shade, hex]) => (
                  <div
                    key={shade}
                    title={`${colorGroup} ${shade}: ${hex}`}
                    style={{
                      backgroundColor: hex,
                      width: '50px',
                      height: '50px',
                      borderRadius: '4px',
                      border: '1px solid #ccc',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      color: parseInt(hex.replace('#', ''), 16) > 0xffffff / 2 ? '#000' : '#fff',
                      fontSize: '0.7rem',
                      userSelect: 'none',
                    }}
                  >
                    {shade}
                  </div>
                ))}
              </div>
            </div>
          );
        })}
      </section>
      <section>
        <h2>AppButton Examples</h2>
        <div style={{ display: 'flex', flexDirection: 'column', gap: '1rem' }}>
          <div>
            <h3>Sizes</h3>
            <div style={{ display: 'flex', gap: '1rem' }}>
              <AppButton size={'sm'}>Small</AppButton>
              <AppButton size={'md'}>Medium</AppButton>
              <AppButton size={'lg'}>Large</AppButton>
            </div>
          </div>

          <div>
            <h3>With Icon</h3>
            <div style={{ display: 'flex', gap: '1rem' }}>
              <AppButton icon={<FontAwesomeIcon icon={faCircleUser} />} iconPosition="left">
                Left Icon
              </AppButton>
              <AppButton icon={<FontAwesomeIcon icon={faCircleUser} />} iconPosition="right">
                Right Icon
              </AppButton>
            </div>
          </div>

          <div>
            <h3>Reversed</h3>
            <div style={{ display: 'flex', gap: '1rem' }}>
              <AppButton animation reversed>
                Default animated
              </AppButton>
              <AppButton reversed icon={<FontAwesomeIcon icon={faCircleUser} />}>
                Icon + Reversed
              </AppButton>
            </div>
          </div>
          <div>
            <h3>Disabled</h3>
            <div style={{ display: 'flex', gap: '1rem' }}>
              <AppButton disabled>Disabled</AppButton>
              <AppButton disabled icon={<FontAwesomeIcon icon={faCircleUser} />}>
                With Icon
              </AppButton>
            </div>
          </div>

          <div>
            <h3>Full Width</h3>
            <AppButton animation fullWidth style={{ width: '100%' }}>
              Full Width Button
            </AppButton>
          </div>
          <div>
            <h3>Custom style</h3>
            <div style={{ display: 'flex', gap: '1rem' }}>
              <AppButton style={{ width: '50%', color: 'red', borderColor: 'red' }}>Width 50% color</AppButton>
              <AppButton style={{ width: '25%', color: 'white', borderColor: 'white', backgroundColor: 'black' }}>
                Width 25% color
              </AppButton>
            </div>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Themes;
